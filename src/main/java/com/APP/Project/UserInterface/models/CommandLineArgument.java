package com.APP.Project.UserInterface.models;

import com.APP.Project.UserInterface.constants.specifications.ArgumentsSpecification;

import java.util.Objects;

/**
 * This class provides a structure for command line argument of the command
 *
 * @author Jayati Thakkar
 * @version 1.0
 */

public class CommandLineArgument {

    private String d_argument;
    private int d_values;

    private ArgumentsSpecification d_specification;

    public CommandLineArgument(String p_argument, int p_values, ArgumentsSpecification p_specification) {
        d_argument = p_argument;
        d_values = p_values;
        d_specification = p_specification;
    }

    public String getD_argument() {
        return d_argument;
    }

    public void setD_argument(String d_argument) {
        this.d_argument = d_argument;
    }

    public int getD_values() {
        return d_values;
    }

    public void setD_values(int d_values) {
        this.d_values = d_values;
    }

    public ArgumentsSpecification getD_specification() {
        return d_specification;
    }

    public void setD_specification(ArgumentsSpecification d_specification) {
        this.d_specification = d_specification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandLineArgument that = (CommandLineArgument) o;
        return d_values == that.d_values && Objects.equals(d_argument, that.d_argument) && d_specification == that.d_specification;
    }

    @Override
    public int hashCode() {
        return Objects.hash(d_argument, d_values, d_specification);
    }
}
