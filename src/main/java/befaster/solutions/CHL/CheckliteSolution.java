package befaster.solutions.CHL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CheckliteSolution {

	private static class Discount {
		private Integer multiple;
		private Integer cost;
		private String freebee = "";
		public Integer getMultiple() {
			return multiple;
		}
		public Integer getCost() {
			return cost;
		}
		public String getFreebee() {
			return freebee;
		}
		public Discount(Integer multiple, Integer cost) {
			this.multiple = multiple;
			this.cost = cost;
		}
		public Discount(Integer multiple, Integer cost, String freebee) {
			this(multiple,cost);
			this.freebee = freebee;
		}
		
	}
	private static class StockItem{
		private String sku;
		public String getSku() {
			return sku;
		}
		public Integer getPrice() {
			return price;
		}
		public List<Discount> getDiscounts() {
			return discounts;
		}
		private Integer price;
		private List<Discount> discounts;
		public StockItem(String sku, Integer price, List<Discount> discounts) {
			this.sku = sku;
			this.price = price;
			if (discounts != null) {
				this.discounts = new ArrayList<>();
				this.discounts.addAll(discounts);
			}
			
		}
//		public String toString() {
//			return this.sku + ":" + this.price + ":" +
//							((this.discount != null) ? this.discount.getMultiple() + ":" + this.discount.getCost()
//							: "");
//		}
	}
	private Map<String,StockItem> catalogue = new HashMap<>();
	public CheckliteSolution() {
		List<Discount> aDiscounts = new ArrayList<>();
		aDiscounts.add(new Discount( 3, 130));
		aDiscounts.add(new Discount( 5, 200));
		catalogue.put("A", new StockItem("A", 50, aDiscounts));
		//System.out.println(catalogue.get("A").toString());
		List<Discount> bDiscounts = new ArrayList<>();
		bDiscounts.add(new Discount( 2, 45));
		catalogue.put("B", new StockItem("B", 30, bDiscounts));
		//System.out.println(catalogue.get("B").toString());
		catalogue.put("C", new StockItem("C", 20, null));
		//System.out.println(catalogue.get("C").toString());
		catalogue.put("D", new StockItem("D", 15, null));
		//System.out.println(catalogue.get("D").toString());
		List<Discount> eDiscounts = new ArrayList<>();
		eDiscounts.add(new Discount(2, 0, "B"));
		catalogue.put("E", new StockItem("E", 40, eDiscounts));
	}
	
	public Integer checklite(String skus) {
		Map<String,Integer> basket = new HashMap<>();
        for (int i = 0; i < skus.length(); i++) {
        	String key = skus.substring(i,i+1);
        	//System.out.print(key);
        	if (!catalogue.keySet().contains(key)) {
        		return Integer.valueOf(-1);
        	}
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
	private Integer calculateLineItemCost(Entry<String, Integer> lineItem) {
		if (catalogue.get(lineItem.getKey()).getDiscounts() != null){
			return calculateBestLineItemDiscount (lineItem);
		}
		else {
			return lineItem.getValue() * catalogue.get(lineItem.getKey()).getPrice();
		}
	}
	
	private Integer calculateBestLineItemDiscount (Entry<String, Integer> lineItem) {
		List<Discount> itemDiscounts = catalogue.get(lineItem.getKey()).getDiscounts();

		//set basic price
		Integer currentPrice = lineItem.getValue() * catalogue.get(lineItem.getKey()).getPrice();
		
		for (Discount discount : itemDiscounts) {
			if( discount.getFreebee().isEmpty())
			{
				Integer discountedPrice = calculateLineItemDiscount(lineItem, discount);
				if ( discountedPrice < currentPrice ) {
					currentPrice = discountedPrice;
				}
			}
		}
		return currentPrice;
	}
	private Integer calculateLineItemDiscount(Entry<String,Integer> lineItem, Discount discount) {
		Integer subTotal = 0;
		if( lineItem.getValue() / discount.getMultiple() > 0 ) {
			subTotal = discount.getCost() * ( lineItem.getValue() / discount.getMultiple());
		}
		if( (lineItem.getValue() % discount.getMultiple()) > 0 ) {
		    subTotal +=
				catalogue.get(lineItem.getKey()).getPrice() * (lineItem.getValue() % discount.getMultiple());
		}
		return subTotal;
	}

}

