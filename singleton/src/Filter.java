import java.util.ArrayList;
import java.util.List;

public class Filter {
    protected int treshold;

    public Filter(int treshold) {
        this.treshold = treshold;
    }

    public List<Integer> filterOut(List<Integer> source) {
        Logger logger = Logger.getInstance();
        List<Integer> result = new ArrayList<>();
        source.forEach(i ->{
            if(i > treshold){
                result.add(i);
                logger.log(String.format("Элемент %s проходит", i));
            }else{
                logger.log(String.format("Элемент %s не проходит", i));
            }
        });
        return result;
    }
}