package ua.in.smartjava.validate;

import org.springframework.validation.annotation.Validated;

import java.util.Date;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Getter;

@Builder
@Validated
@Aws
@Getter
public class Session {

    @NotNull
    @Size(min = 1, max = 10)
    private String name;

    @Future
    private Date plannedDate;

    @AssertTrue
    private boolean attended;

    @Null(groups = GroupTime.class)
    private String group;
}

interface GroupTime {}