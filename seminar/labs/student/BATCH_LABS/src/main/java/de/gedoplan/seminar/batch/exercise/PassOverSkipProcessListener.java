package de.gedoplan.seminar.batch.exercise;

import java.util.ArrayList;
import java.util.List;

import javax.batch.api.chunk.listener.SkipProcessListener;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.logging.Log;

@Named
public class PassOverSkipProcessListener implements SkipProcessListener {

  @Inject
  Log log;

  @Inject
  JobContext jobContext;

  @SuppressWarnings("unchecked")
  @Override
  public void onSkipProcessItem(Object item, Exception ex) throws Exception {
    Object transientUserData = this.jobContext.getTransientUserData();
    List<Object> invalidItems;
    if (transientUserData == null) {
      invalidItems = new ArrayList<>();
      this.jobContext.setTransientUserData(invalidItems);
    } else {
      invalidItems = (List<Object>) transientUserData;
    }

    invalidItems.add(item);
  }
}
