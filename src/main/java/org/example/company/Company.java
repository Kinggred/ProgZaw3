package org.example.company;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Company {
    private String name;
    private final List<Department> departments = new ArrayList<>();

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Department> getDepartments() {
        return departments;
    }

    public Company addDepartment(Department department) {
        departments.add(department);
        return this;
    }

    public List<Worker> getWorkers() {
        List<Worker> workers = new ArrayList<>();
        for (Department department : departments) {
            workers.addAll(department.getWorkerList());
        }
        return workers;
    }
}
