package it.perigea.formazione.aggregator.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.perigea.formazione.aggregator.entity.ClinicalStatusEntity;
import it.perigea.formazione.aggregator.repository.ClinicalStatusRepository;


@Service
public class ClinicalStatusImpl {

	@Autowired
	private ClinicalStatusRepository clinicalRepository;

	//metodo per il conteggio degli stati clinici nell'intervallo di date passate come input
	public  ClinicalStatusEntity CountForEachStateInADateRange(String dataInizio, String dataFine) throws ParseException {
		ClinicalStatusEntity clinicalEntity = new ClinicalStatusEntity();
		Date startDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(dataInizio + "T00:00:00.000");
		Date endDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(dataFine + "T00:00:00.000");		
		int sommaCasi=0;
		int sommaNoStatiClinici=0;
		int sommaGuariti=0;
		int sommaDeceduti=0;
		int sommaAsintomatici=0;
		int sommaSintomatici=0;
		int sommaSintGuariti=0;
		int sommaSintDeced=0;
		int sommaAsintGuar=0;
		int sommaAsintDeced=0;
		List<ClinicalStatusEntity> list = clinicalRepository.findAllByDataInizioSintomiBetween(startDate, endDate);
		ClinicalStatusEntity clinicalStatusEntity = new ClinicalStatusEntity();
		for(int i=0; i<list.size(); i++) {
			clinicalStatusEntity=list.get(i);
			int casiTot=clinicalStatusEntity.getCasiTotali();
			sommaCasi=sommaCasi+casiTot;
			int noStaCli=clinicalStatusEntity.getNessunoStatoClinico();
			sommaNoStatiClinici=sommaNoStatiClinici+noStaCli;
			int gua=clinicalStatusEntity.getGuariti();
			sommaGuariti=sommaGuariti+gua;
			int dec=clinicalStatusEntity.getDeceduti();
			sommaDeceduti=sommaDeceduti+dec;
			int asi=clinicalStatusEntity.getAsintomatici();
			sommaAsintomatici=sommaAsintomatici+asi;
			int sin=clinicalStatusEntity.getConSintomi();
			sommaSintomatici=sommaSintomatici+sin;
			int sinGua=clinicalStatusEntity.getConSintomiGuariti();
			sommaSintGuariti=sommaSintGuariti+sinGua;
			int sinDec=clinicalStatusEntity.getConSintomiDecessi();
			sommaSintDeced=sommaSintDeced+sinDec;
			int asiGua=clinicalStatusEntity.getAsintomaticiGuariti();
			sommaAsintGuar=sommaAsintGuar+asiGua;
			int asiDec=clinicalStatusEntity.getAsintomaticiDeceduti();
			sommaAsintDeced=sommaAsintDeced+asiDec;
			clinicalEntity.setCasiTotali(sommaCasi);
			clinicalEntity.setNessunoStatoClinico(sommaNoStatiClinici);
			clinicalEntity.setGuariti(sommaGuariti);
			clinicalEntity.setDeceduti(sommaDeceduti);
			clinicalEntity.setAsintomatici(sommaAsintomatici);
			clinicalEntity.setConSintomi(sommaSintomatici);
			clinicalEntity.setConSintomiGuariti(sommaSintGuariti);
			clinicalEntity.setConSintomiDecessi(sommaSintDeced);
			clinicalEntity.setAsintomaticiGuariti(sommaAsintGuar);
			clinicalEntity.setAsintomaticiDeceduti(sommaAsintDeced);
			clinicalEntity.setDate("/");
		};
		return clinicalEntity;
	}
}