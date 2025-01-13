package controller;

import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import dao.UserDao;
import model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/version")
    public int version() {
        return HTTPResponse.SC_OK;
    }

    @GetMapping("/users")
    @PreAuthorize("#username == authentication.name or hasRole('ADMIN')")
    public int getUsers() {
        return HTTPResponse.SC_OK;
    }


    @GetMapping("/users/{username}")
    @PreAuthorize("#username == authentication.name or hasRole('ADMIN')")
    public User getUserByName(@PathVariable("username") String username) {

        return new UserDao().getUserByUserName(username);
    }

}