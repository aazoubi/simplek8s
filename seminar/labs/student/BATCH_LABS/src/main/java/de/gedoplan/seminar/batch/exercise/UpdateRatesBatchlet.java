package de.gedoplan.seminar.batch.exercise;

import de.gedoplan.seminar.batch.demo.service.BatchInputService;
import de.gedoplan.seminar.batch.exercise.domain.CallRegion;
import de.gedoplan.seminar.batch.exercise.domain.Rate;
import de.gedoplan.seminar.batch.exercise.persistence.RateRepository;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;

import javax.batch.api.AbstractBatchlet;
import javax.batch.api.BatchProperty;
import javax.batch.runtime.BatchStatus;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Batchlet for updating article data from an XML file.
 *
 */
@Named
public class UpdateRatesBatchlet extends AbstractBatchlet {

  @Inject
  BatchInputService batchInputService;

  @Inject
  RateRepository rateRepository;

  @Inject
  @BatchProperty
  String filename;

  @Override
  public String process() throws Exception {
    if (this.filename == null) {
      this.filename = "rates.properties";
    }

    Properties ratesProperties = new Properties();
    try (InputStream is = this.batchInputService.getFileAsStream(this.filename)) {
      ratesProperties.load(is);
    }

    ratesProperties.entrySet().stream()
        .map(e -> new Rate(CallRegion.valueOf(e.getKey().toString()), new BigDecimal(e.getValue().toString())))
        .forEach(this.rateRepository::merge);

    return BatchStatus.COMPLETED.toString();
  }

}
