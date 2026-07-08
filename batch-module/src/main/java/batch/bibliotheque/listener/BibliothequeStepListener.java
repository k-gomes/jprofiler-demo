package batch.bibliotheque.listener;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class BibliothequeStepListener implements StepExecutionListener {

  private static final Logger LOG = LoggerFactory.getLogger(BibliothequeStepListener.class);

  /**
   *
   * @param stepExecution
   */
  @Override
  public void beforeStep(StepExecution stepExecution)
  {
    //Nothing to do
  }

  /**
   *
   * @param stepExecution
   * @return
   */
  @Override
  public ExitStatus afterStep(StepExecution stepExecution)
  {
    //Nothing to do
    return ExitStatus.COMPLETED;
  }

}
