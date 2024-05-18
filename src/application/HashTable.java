package application;

class HashTable {
    private int tableSize;
    private Vertex[] table;

    HashTable(int tableSize) {
        this.tableSize = tableSize;
        this.table=new Vertex[tableSize];
        for (int i = 0; i < tableSize; i++) {
            table[i] = null; 
        }
    }

    public void put(Vertex value) {
        int hash = getHash(value.getCityName());
        while (table[hash] != null && !table[hash].getCityName().equals(value.getCityName())) {
            hash = (hash + 1) % tableSize;
        }
        table[hash] = value;
    }

    public Vertex getVertex(String key) {
        int hash = getHash(key);
        while (table[hash] != null && !table[hash].getCityName().equals(key)) {
            hash = (hash + 1) % tableSize;
        }
        if (table[hash] == null) {
            return null;
        } else {
            return table[hash];
        }
    }
    
    public int getVertexIndex(String key) {
        int hash = getHash(key);
        while (table[hash] != null && !table[hash].getCityName().equals(key)) {
            hash = (hash + 1) % tableSize;     
        }
        if (table[hash] == null) {
            return -1;
        } else {
            return hash;
        }
    }

    private int getHash(String key) {
        int hash = key.hashCode() % tableSize;
        if (hash < 0) {
            hash += tableSize;
        }
        return hash;
    }

	public int getTableSize() {
		return tableSize;
	}

	public Vertex[] getTable() {
		return table;
	}
}
