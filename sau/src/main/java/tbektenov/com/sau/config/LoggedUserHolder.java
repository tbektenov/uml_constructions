package tbektenov.com.sau.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import tbektenov.com.sau.models.user.UserEntity;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LoggedUserHolder {
    private UserEntity loggedUser = null;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public UserEntity getLoggedUser() {
        if (loggedUser == null) {
            loggedUser = customUserDetailsService.getLoggedUser();
        }
        return loggedUser;
    }

    public void clear() {
        loggedUser = null;
    }
}

