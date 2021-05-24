package com.bank.api.entity;

public class Contractor {
    private long id;
    private String name;
    private boolean  corporation;
    private String description;

    public Contractor() {
    }

    public Contractor(String name) {
        this.name = name;
    }

    public Contractor(String name, boolean corporation, String description) {
        this.name = name;
        this.corporation = corporation;
        this.description = description;
    }

    public Contractor(long id, String name, boolean corporation) {
        this.id = id;
        this.name = name;
        this.corporation = corporation;
    }

    public Contractor(long id, String name, boolean corporation, String description) {
        this.id = id;
        this.name = name;
        this.corporation = corporation;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCorporation() {
        return corporation;
    }

    public void setCorporation(boolean corporation) {
        this.corporation = corporation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contractor that = (Contractor) o;

        if (id != that.id) return false;
        if (corporation != that.corporation) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (corporation ? 1 : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
