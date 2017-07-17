package com.possoajudar.app.Mockito;

import com.possoajudar.app.R;
import com.possoajudar.app.application.service.ILoginView;
import com.possoajudar.app.application.service.login.LoginPresenter;
import com.possoajudar.app.application.service.login.LoginService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by renato on 17/07/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestMockito {
    @Mock
    private ILoginView view;
    @Mock
    private LoginService service;
    @Mock
    private LoginPresenter presenter;


    /* IntelliJ IDEA 2016.3 Help
        Press Alt+Insert and select Test Method from the Generate menu.
    */
    @Before
    public void setUp() throws Exception {
        presenter = new LoginPresenter(view, service);
    }


    /*
    Botão auxiliar do mouse: Generate + Test Method
     */
    @Test
    public void shouldShowErrorMessageWhenUsernameIsEmpty() throws Exception {
        when(view.getUsername()).thenReturn("");
        presenter.onLoginClicked();
        verify(view).showUsernameError(R.string.strLyLoginUsername_error);
    }

    /*
    Deve exiber uma mensagem quando a senha do usuário for vazia
     */
    @Test
    public void shouldShowErrorMessageWhenUserPasswordIsEmpty() throws Exception {
        when(view.getUsername()).thenReturn("developer");
        when(view.getPassword()).thenReturn("");
        presenter.onLoginClicked();
        verify(view).showPasswordError(R.string.strLyLoginPassword_error);
    }


    @Test
    public void shouldStartMainActivityWhenUsernameAndPasswordAreCorrect() throws Exception {
        when(view.getUsername()).thenReturn("developer");
        when(view.getPassword()).thenReturn("developer");
        when(service.login("developer", "developer")).thenReturn(true);
        presenter.onLoginClicked();
        verify(view).startMainActivity();
    }

    @Test
    public void shouldShowLoginErrorWhenUsernameAndPasswordAreInvalid() throws Exception {
        when(view.getUsername()).thenReturn("developer");
        when(view.getPassword()).thenReturn("development");
        when(service.login("developer", "development")).thenReturn(false);
        presenter.onLoginClicked();
        verify(view).showLoginError(R.string.strLyLoginloginfailed);
    }

}
