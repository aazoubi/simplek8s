package de.gedoplan.seminar.batch.exercise;

import de.gedoplan.seminar.batch.demo.service.BatchInputService;
import de.gedoplan.seminar.batch.exercise.domain.Customer;
import org.apache.commons.logging.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.AbstractItemReader;
import javax.inject.Inject;
import javax.inject.Named;


@Named
public class CustomerCsvItemReader extends AbstractItemReader {

  @Inject
  BatchInputService batchInputService;

  @Inject
  Log log;

  @Inject
  @BatchProperty
  String filename;

  private BufferedReader reader;

  @Override
  public void open(Serializable checkpoint) throws IOException {
    if (this.filename == null) {
      this.filename = "customers.csv";
    }

    log.info("filename ist null....");

    this.reader = new BufferedReader(new InputStreamReader(this.batchInputService.getFileAsStream(this.filename)));
  }

  @Override
  public Customer readItem() throws IOException {

    String line = this.reader.readLine();
    if (line == null) {
      return null;
    }

    String[] fields = line.split("\\|", -1);
    return new Customer(fields[0], fields[1], fields[2]);
  }

  @Override
  public void close() throws IOException {
    this.reader.close();
  }
}
