package it.cnr.missioni.dashboard.checkmassimale;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.el.model.search.builder.UserSearchBuilder;
import it.cnr.missioni.model.user.User;

/**
 * @author Salvia Vito
 */
public class GetUser extends IControlCheckMassimale.AbstractControlCheckMassimale {

    protected GetUser() {
    }

    public static IControlCheckMassimale newGetUser() {
        return new GetUser();
    }
	
	/**
	 * @throws Exception 
	 * 
	 */
	@Override
	public void check() throws Exception {
		User user = ClientConnector
				.getUser(UserSearchBuilder.getUserSearchBuilder().withId(this.checkMassimale.getMissione().getIdUser()))
				.getUsers().get(0);
		this.checkMassimale.setLivello( user.getDatiCNR().getLivello().name());
		this.checkMassimale.setUser(user);
		this.nextControl.check();

	}

}
