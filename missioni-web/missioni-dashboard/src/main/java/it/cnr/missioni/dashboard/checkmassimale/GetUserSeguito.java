package it.cnr.missioni.dashboard.checkmassimale;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.el.model.search.builder.UserSearchBuilder;
import it.cnr.missioni.model.user.User;

/**
 * @author Salvia Vito
 */
public class GetUserSeguito extends IControlCheckMassimale.AbstractControlCheckMassimale {

    protected GetUserSeguito() {
    }

    public static IControlCheckMassimale newGetUserSeguito() {
        return new GetUserSeguito();
    }
	
	/**
	 * @throws Exception 
	 * 
	 */
	@Override
	public void check() throws Exception {
		if (checkMassimale.getMissione().getIdUserSeguito() != null) {
			User userSeguito = ClientConnector.getUser(
					UserSearchBuilder.getUserSearchBuilder().withId(checkMassimale.getMissione().getIdUserSeguito()))
					.getUsers().get(0);
			if (userSeguito.getDatiCNR().getLivello().getStato() < checkMassimale.getUser().getDatiCNR().getLivello()
					.getStato())
				checkMassimale.setLivello(userSeguito.getDatiCNR().getLivello().name());
		}
		this.nextControl.check();

	}

}
