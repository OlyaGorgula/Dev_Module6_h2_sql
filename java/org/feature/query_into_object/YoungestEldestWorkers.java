package org.feature.query_into_object;

public class YoungestEldestWorkers {
    private final String type;
    private final String name;
    private final String birthday;

    public YoungestEldestWorkers(String type, String name, String birthday) {
        this.type = type;
        this.name = name;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "YoungestEldestWorkers{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
