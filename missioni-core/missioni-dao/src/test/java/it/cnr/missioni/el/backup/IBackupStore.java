package it.cnr.missioni.el.backup;

import java.util.ArrayList;
import java.util.List;

import it.cnr.missioni.model.configuration.Direttore;
import it.cnr.missioni.model.configuration.Massimale;
import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.model.configuration.QualificaUser;
import it.cnr.missioni.model.configuration.RimborsoKm;
import it.cnr.missioni.model.configuration.TipologiaSpesa;
import it.cnr.missioni.model.configuration.UrlImage;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.prenotazione.Prenotazione;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;
import it.cnr.missioni.model.user.User;

public interface IBackupStore<E> {
	
	void setList(List<E> list);
	
	List<E> getList();
	
	public class NazioneBackupStore implements IBackupStore<Nazione>{

		private List<Nazione> list = new ArrayList();

		public List<Nazione> getList() {
			return list;
		}

		public void setList(List<Nazione> list) {
			this.list = list;
		}
		
	}
	
	public class TipologiaSpesaBackupStore implements IBackupStore<TipologiaSpesa>{

		private List<TipologiaSpesa> list = new ArrayList();

		public List<TipologiaSpesa> getList() {
			return list;
		}

		public void setList(List<TipologiaSpesa> list) {
			this.list = list;
		}
	}
	
	public class MassimaleBackupStore implements IBackupStore<Massimale>{

		private List<Massimale> list = new ArrayList();

		public List<Massimale> getList() {
			return list;
		}

		public void setList(List<Massimale> list) {
			this.list = list;
		}
	}
	
	public class DirettoreBackupStore implements IBackupStore<Direttore>{

		private List<Direttore> list = new ArrayList();

		public List<Direttore> getList() {
			return list;
		}

		public void setList(List<Direttore> list) {
			this.list = list;
		}
	}
	
	public class UrlImageBackupStore implements IBackupStore<UrlImage>{

		private List<UrlImage> list = new ArrayList();

		public List<UrlImage> getList() {
			return list;
		}

		public void setList(List<UrlImage> list) {
			this.list = list;
		}
	}
	
	public class QualificaBackupStore implements IBackupStore<QualificaUser>{

		private List<QualificaUser> list = new ArrayList();

		public List<QualificaUser> getList() {
			return list;
		}

		public void setList(List<QualificaUser> list) {
			this.list = list;
		}
	}
	
	public class RimborsoKmBackupStore implements IBackupStore<RimborsoKm>{

		private List<RimborsoKm> list = new ArrayList();

		public List<RimborsoKm> getList() {
			return list;
		}

		public void setList(List<RimborsoKm> list) {
			this.list = list;
		}
	}
	
	public class UserBackupStore implements IBackupStore<User>{

		private List<User> list = new ArrayList();

		public List<User> getList() {
			return list;
		}

		public void setList(List<User> list) {
			this.list = list;
		}
		
	}
	
	public class MissioneBackupStore implements IBackupStore<Missione>{

		private List<Missione> list = new ArrayList();

		public List<Missione> getList() {
			return list;
		}

		public void setList(List<Missione> list) {
			this.list = list;
		}
		
	}
	
	public class PrenotazioneBackupStore implements IBackupStore<Prenotazione>{

		private List<Prenotazione> list = new ArrayList();

		public List<Prenotazione> getList() {
			return list;
		}

		public void setList(List<Prenotazione> list) {
			this.list = list;
		}
		
	}
	public class VeicoloCnrBackupStore implements IBackupStore<VeicoloCNR>{

		private List<VeicoloCNR> list = new ArrayList();

		public List<VeicoloCNR> getList() {
			return list;
		}

		public void setList(List<VeicoloCNR> list) {
			this.list = list;
		}
		
	}
	
}


