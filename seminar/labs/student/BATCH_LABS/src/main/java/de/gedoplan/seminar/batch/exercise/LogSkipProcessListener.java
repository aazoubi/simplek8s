package de.gedoplan.seminar.batch.exercise;

import javax.batch.api.chunk.listener.SkipProcessListener;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.logging.Log;

@Named
public class LogSkipProcessListener implements SkipProcessListener {

  @Inject
  Log log;

  @Override
  public void onSkipProcessItem(Object item, Exception ex) throws Exception {
    this.log.info("Skipping " + item + " due to " + ex.getClass());
  }
}
