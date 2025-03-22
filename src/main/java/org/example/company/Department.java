package org.example.company;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private String name;
    private final List<Worker> workerList = new ArrayList<>();

    public String getName() {
        return name;
    }
    public Department setName(String name) {
        this.name = name;
        return this;
    }

    public List<Worker> getWorkerList() {
        return workerList;
    }

    public Department addWorker(Worker worker) {
        this.workerList.add(worker);
        return this;
    }

}
