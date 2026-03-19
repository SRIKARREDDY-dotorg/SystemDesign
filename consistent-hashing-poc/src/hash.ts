import * as crypto from "crypto";

export function hash(value: string): number {
    const hex = crypto.createHash("md5").update(value).digest("hex").slice(0,8);
    return parseInt(hex, 16);
}