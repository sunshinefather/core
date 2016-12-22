package com.palmlink.core.lock;

public enum LockStack {
    
    INVENTORYLOCK("/NAIER/INVENTORYLOCK"), PACKLOCK("/NAIER/PACKLOCK"), ADDSKULOCK("/NAIER/ADDSKULOCK");
    
    private String key;
    
    private LockStack(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
