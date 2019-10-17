package befaster.solutions.CHL;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import befaster.runner.SolutionNotImplementedException;

public class CheckliteSolution {

	private static class Discount {
		private Integer multiple;
		private Integer cost;
		public Discount(Integer multiple, Integer cost) {
			this.multiple = multiple;
			this.cost = cost;
		}
	}
	private static class StockItem{
		private String sku;
		private Integer price;
		private Discount discount;
		public StockItem(String sku, Integer price, Discount discount) {
			this.sku = sku;
			this.price = price;
			this.discount = discount;
		}
	}
	private Map<String,StockItem> catalogue = new HashMap<>();
	
	public CheckliteSolution() {
		catalogue.put("A", new StockItem("A", 50, new Discount( 3, 130)));
		catalogue.put("B", new StockItem("B", 30, new Discount( 2, 45)));
		catalogue.put("C", new StockItem("C", 20, null));
		catalogue.put("D", new StockItem("D", 15, null));
	}
	
	private Map<String,Integer> basket = new HashMap<>(); 
	public Integer checklite(String skus) {
        for (int i = 0; i < skus.length(); i++) {
        	String key = skus.substring(i,i+1);
        	if ( catalogue.containsKey(key)){
        		if (!basket.containsKey(key)) {
        			basket.put(key, Integer.valueOf(1));
        		}else {
        			basket.put(key, basket.get(key) + 1);
        		}
        	}
        }
        Integer basketTotal = Integer.valueOf(0);
        for( Entry<String,Integer> lineItem : basket.entrySet()) {
        	basketTotal += calculateLineItemCost(lineItem);
        }
        return basketTotal;
    }
	private Integer calculateLineItemCost(Map.Entry<String,Integer>) {
		// TODO Auto-generated method stub
		return null;
	}
}

