package net.andreinc.jsr380validation.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.andreinc.jbvext.annotations.date.IsDate;
import net.andreinc.jbvext.annotations.misc.OneOfStrings;
import net.andreinc.jbvext.annotations.str.Alphanumeric;
import net.andreinc.jbvext.annotations.str.AlphanumericSpace;
import net.andreinc.jbvext.annotations.str.EndsWith;
import net.andreinc.jbvext.annotations.str.StartsWith;
import net.andreinc.jbvext.utils.SimpleValidation;
import org.hibernate.validator.constraints.Email;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
public class ExampleController {

    @PostMapping("/user/")
    public ResponseEntity<?> createUser(@RequestBody CreateUserReq request) {
        // If the TestBodyReq fails the validation a BeanValidationException will be thrown.
        // The error will be caught in the BeanValidationExceptionCatch.java class
        SimpleValidation.validate(request);
        System.out.println("Request received: " + request);
        return status(OK).build();
    }

    @PostMapping("/post/")
    public ResponseEntity<?> createPost(@RequestBody CreatePostReq request) {
        SimpleValidation.validate(request);
        System.out.println("Request received: " + request);
        return status(OK).build();
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class CreateUserReq {

    @Alphanumeric
    @Size(min = 6, max = 32)
    private String userName;

    @Email
    private String email;

    @OneOfStrings({"MALE", "FEMALE"})
    private String sex;

    @IsDate("yyyy-MM-dd")
    private String dateOfBirth;

    @StartsWith("A")
    @EndsWith("00")
    private String appCode;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class CreatePostReq {

    @Email
    private String authorEmail;

    @AlphanumericSpace
    @Size(min = 3, max = 256)
    private String title;

    @IsDate("dd-MM-yyyy")
    private String publishDate;

    @NotNull
    @Size(min = 0, max = 10000)
    private String text;

    private List<@Alphanumeric String> tags;

}
