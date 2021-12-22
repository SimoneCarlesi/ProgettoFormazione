package it.perigea.formazione.aggregator.service;

import java.util.Date;
import java.util.List;


import it.perigea.formazione.aggregator.entity.SomministrationsEntity;


public interface SomministrationsService  {
	
	public int getSomministrationsBySingleDose();
	
	public int getSomministrationsByDoubleDose();
	
	public int getSomministrationsBySingleDoseAndProvince(String province);
	
	public int getSomministrationsByDoubleDoseAndProvince(String province);
	
	public List<SomministrationsEntity> fromHigherDoseToLowerDoseInLombardia();
	
	public List<SomministrationsEntity> fromHigherDoseToLowerDoseInProvince(String province);
	
	public SomministrationsEntity findComuneWithMoreVaccinatedSingleDose();
	
	public SomministrationsEntity findComuneWithMoreVaccinatedDoubleDose();
	
	public SomministrationsEntity findComuneWithLessVaccinatedSingleDose();
	
	public SomministrationsEntity findComuneWithLessVaccinatedDoubleDose();
	
	public SomministrationsEntity findComuneWithMoreVaccinatedSingleDoseWithProvince(String province);
	
	public SomministrationsEntity findComuneWithMoreVaccinatedDoubleDoseWithProvince(String province);
	
}
