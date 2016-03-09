package it.cnr.missioni.model.chainOfResponsibility;

/**
 * @author Salvia Vito
 */
public interface IControl {

	void setNextControl(IControl control);
	
	void check();
	
}
