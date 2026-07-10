package com.company.workforce.constants;

public enum EmployeeSortField {

    ID("id"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    EMAIL("email"),
    SALARY("salary"),
    CREATED_AT("createdAt"),
    UPDATED_AT("updatedAt");

    private final String fieldName;

    EmployeeSortField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}