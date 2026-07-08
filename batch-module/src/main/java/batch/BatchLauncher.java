package batch;

import java.util.Date;
import java.util.Scanner;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class BatchLauncher {

  private static final Logger LOG = LoggerFactory.getLogger(BatchLauncher.class);


  public static void main(String[] args) throws Exception
  {
    Scanner scanner = new Scanner(System.in);
    System.out.print("--------    Attente du lancement de Jprofiler    --------");
    String ready = scanner.next();

    if(ready != null)
    {
      LOG.info("Lancement du batch bibliotheque");
      // ouverture du contexte
      ClassPathXmlApplicationContext contexte =
          new ClassPathXmlApplicationContext("bibliotheque/job/bibliotheque_job.xml");
      // recuperation du lanceur
      JobLauncher jobLauncher = contexte.getBean(JobLauncher.class);
      // recuperation du job
      Job job = contexte.getBean(Job.class);
      // demarrage du job
      jobLauncher.run(job, getJobParameters());
      // fermeture du contexte
      contexte.close();
    }
  }

  private static JobParameters getJobParameters()
  {
    JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
    jobParametersBuilder.addDate("date_lancement", new Date());
    return jobParametersBuilder.toJobParameters();
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
    var configurer = new PropertySourcesPlaceholderConfigurer();
    var locations = new Resource[] {
        new ClassPathResource("properties/application.properties")};
    configurer.setLocations(locations);
    configurer.setFileEncoding("UTF-8");
    return configurer;
  }
}
