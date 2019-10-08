package de.gedoplan.seminar.batch.exercise;

import de.gedoplan.seminar.batch.demo.service.BatchInputService;
import de.gedoplan.seminar.batch.exercise.domain.Customer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.AbstractItemReader;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.logging.Log;

/**
 * Item reader for customer records from CSV file.
 * This reader manages checkpoints and is therefore restartable.
 */
@Named
public class CustomerCsvItemReaderWithCheckpoint extends AbstractItemReader {

  @Inject
  Log log;

  @Inject
  BatchInputService batchInputService;

  @Inject
  @BatchProperty
  String filename;

  private BufferedReader reader;

  private int linesRead;

  @Override
  public void open(Serializable checkpoint) throws IOException {
    if (this.filename == null) {
      this.filename = "customers.csv";
    }

    this.reader = new BufferedReader(new InputStreamReader(this.batchInputService.getFileAsStream(this.filename)));
    this.linesRead = 0;

    if (checkpoint instanceof Integer) {
      int linesToSkip = (Integer) checkpoint;
      this.log.debug("\nRESTART - skipping " + linesToSkip + " lines");
      while (true) {
        if (this.linesRead >= linesToSkip) {
          break;
        }
        if (this.reader.readLine() == null) {
          break;
        }
        ++this.linesRead;
      }
    } else {
      this.log.debug("\nSTART");
    }
  }

  @Override
  public Customer readItem() throws IOException {
    String line = this.reader.readLine();
    if (line == null) {
      return null;
    }

    ++this.linesRead;

    String[] fields = line.split("\\|", -1);
    return new Customer(fields[0], fields[1], fields[2]);
  }

  @Override
  public Serializable checkpointInfo() {
    this.log.debug("checkpointInfo: " + this.linesRead);
    return this.linesRead;
  }

  @Override
  public void close() throws IOException {
    this.reader.close();
  }
}
