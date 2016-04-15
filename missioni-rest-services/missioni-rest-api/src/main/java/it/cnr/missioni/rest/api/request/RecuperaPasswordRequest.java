package it.cnr.missioni.rest.api.request;

import org.hibernate.validator.constraints.NotBlank;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author Salvia Vito
 */
@XmlRootElement(name = "NotificationMissionRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class RecuperaPasswordRequest implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2367776932311366369L;
	//
    @NotBlank(message = "Il Parametro email non deve essere vuoto.")
    private String email;
    @NotBlank(message = "Il Parametro userName non deve essere vuoto.")
    private String userName;
    @NotBlank(message = "Il Parametro email non deve essere vuoto.")
    private String userSurname;
    @NotBlank(message = "Il Parametro password non deve essere vuoto.")
    private String password;

    public RecuperaPasswordRequest() {
    }

    /**
     * @return {@link String}
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param missionID
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return {@link String}
     */
    public String getUserName() {
		return userName;
	}

    /**
     * 
     * @param userName
     */
	public void setUserName(String userName) {
		this.userName = userName;
	}

    /**
     * @return {@link String}
     */
	public String getUserSurname() {
		return userSurname;
	}

	/**
	 * 
	 * @param userSurname
	 */
	public void setUserSurname(String userSurname) {
		this.userSurname = userSurname;
	}

    /**
     * @return {@link String}
     */
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "RecuperaPasswordRequest [email=" + email + ", userName=" + userName + ", userSurname=" + userSurname
				+ ", password=" + password + "]";
	}


}
