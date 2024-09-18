## Requirements

* Generate a unique short url, for the given lengthy url
* Redirect the user to the original url, when short url is accessed.
* Users can customize the URL
* Support the link expiration
* Provide the analytics on the link usage

* Highly available system
* Low latency 
* Scalable, durable & Secure

## Capacity Estimation

### Assumptions
* Perday URL shortening requests: 1,000,000
* Read write ratio: (100:1) for each URL creation, 100 redirects
* Peak Traffic: 10 times the average load
* URL lengths: Average length of 100 characters

#### ThroughPut (TPS)
* Average Writes (TPS) 1000000/86400 = 12
* Peak Write (TPS) = 12*10 = 120TPS

Since Read to Write ration is 100:1
* Average redirects (Read TPS) is 12*100 = 1200 TPS
* Peak Redirects (TPS) = 1200*10 = 12,000 TPS

#### Storage Estimation
For each shortened URL storage
* Short URL: 7 characters (base 62)
* Original URL: 100 chars avg
* Creation date: 8 bytes (timestamp)
* Expiration date: 8 bytes (timestamp)
* Redirect count (4 bytes)

Total storage per url:
* Sum: 7+100+8+8+4 ~= 127

Storage requirements per year
Total URL per year = 1000000*365 = 365,000,000
Total storage per year: ~= 46.4 GB

#### Bandwidth Estimation

Assumptions: There are total of 500 bytes for the HTTP 301 redirects header

* Total Read bandwidth per day: 1,000,000 * 500 * 100(Read write ratio) ~= 50,000,000,000 = 50 GB
* Peak bandwidth ~= 12,000 TPS * 500 bytes = 6,000,000 = 6 MB/s

#### Caching Estimation

Read heavy system will need caching for the efficient reads
* Follow 80-20 rule, 20% of URL's will be redirected (read calls)
* Cal: 1,000,000 writes, 0.2*1,000,000*127bytes = 25,400,000 ~= 25.4MB average
* Let's assume a cache hit ratio of 90%, rest 10% of will be read calls ~= 1200*0.1 = 120TPS
* We can use DDB or Cassandra for this 

#### Infrastructure sizing (TBD)