package batch.bibliotheque.partitionner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import batch.bibliotheque.listener.BibliothequeJobListener;

public class BibliothequePartitionner implements Partitioner {

  private static final Logger LOG = LoggerFactory.getLogger(BibliothequePartitionner.class);

  /**
   * Méthode qui crée des partitions
   *
   * @param gridSize
   * @return
   */
  @Override
  public Map<String, ExecutionContext> partition(int gridSize) {

    // Création des partitions pour toutes les lettres de l'alphabet
    LOG.info("PARTITIONNING");
    Map<String, ExecutionContext> map = new HashMap<>(gridSize);
    String cst = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    char[] chars = cst.toCharArray();

    for (char lettre: chars)
    {
      /** new context .*/
      ExecutionContext context = new ExecutionContext();
      context.put("KEY", String.valueOf(lettre));
      map.put("BIBLIOTHEQUE_PARTITION_".concat(String.valueOf(lettre)), context);
    }

    return map;
  }
}
