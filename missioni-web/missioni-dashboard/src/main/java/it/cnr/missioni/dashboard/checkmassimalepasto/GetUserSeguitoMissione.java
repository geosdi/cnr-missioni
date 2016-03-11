package it.cnr.missioni.dashboard.checkmassimalepasto;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.el.model.search.builder.UserSearchBuilder;
import it.cnr.missioni.model.user.User;

/**
 * 
 * Recupera l'user a seguito
 * 
 * @author Salvia Vito
 */
public class GetUserSeguitoMissione extends IControlCheckMassimale.AbstractControlCheckMassimale {

    protected GetUserSeguitoMissione() {
    }

    public static IControlCheckMassimale newGetUserSeguito() {
        return new GetUserSeguitoMissione();
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