package com.brodzik.adrian.simplefilesynchronizer.data;

public enum SyncDirection {
    UNIDIRECTIONAL_1("unidirectional", "A --> B"),
    UNIDIRECTIONAL_2("unidirectional", "A <-- B"),
    BIDIRECTIONAL("bidirectional", "A <-> B");

    private final String name;
    private final String symbol;

    SyncDirection(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }
}
