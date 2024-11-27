package com.srikar.library;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemberManager {
    private static MemberManager INSTANCE;
    private final Map<String, Member> memberCatalog;
    private MemberManager() {
        memberCatalog = new ConcurrentHashMap<>();
    }

    public static MemberManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MemberManager();
        }
        return INSTANCE;
    }

    public void addMember(Member member) {
        memberCatalog.put(member.getId(), member);
    }
    public void removeMember(String memberId) {
        memberCatalog.remove(memberId);
    }
    public boolean isValidMember(String memberId) {
        return memberCatalog.containsKey(memberId);
    }
}
