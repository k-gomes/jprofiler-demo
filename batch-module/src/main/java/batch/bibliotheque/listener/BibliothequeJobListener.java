package batch.bibliotheque.listener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;


public class BibliothequeJobListener implements JobExecutionListener {

  private static final Logger LOG = LoggerFactory.getLogger(BibliothequeJobListener.class);

  /**
   * Affichage des logs pour le début de la journée et gestion des variables pour les logs
   *
   * @param jobExecution
   */
  @Override
  public void beforeJob(JobExecution jobExecution)
  {
    LOG.info("---------------------------------------------------------");
    LOG.info("FIN DE L'INSERTION DES DONNEES DANS LA TABLE BIBLIOTHEQUE");
    LOG.info("---------------------------------------------------------");
  }

  @Override
  public void afterJob(JobExecution jobExecution)
  {
    //Nothing to do
  }
}
