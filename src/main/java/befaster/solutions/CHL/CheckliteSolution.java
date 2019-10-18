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
		private String freeSku = "";
		public Integer getMultiple() {
			return multiple;
		}
		public Integer getCost() {
			return cost;
		}
		public String getFreeSku() {
			return freeSku;
		}
		public Discount(Integer multiple, Integer cost) {
			this.multiple = multiple;
			this.cost = cost;
		}
		public Discount(Integer multiple, Integer cost, String freebee) {
			this(multiple,cost);
			this.freeSku = freebee;
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
		private List<Discount> discounts = new ArrayList<>();
		public StockItem(String sku, Integer price, List<Discount> discounts) {
			this.sku = sku;
			this.price = price;
			if (discounts != null) {
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
		// assume best discounts are listed first
		List<Discount> aDiscounts = new ArrayList<>();
		aDiscounts.add(new Discount( 5, 200));
		aDiscounts.add(new Discount( 3, 130));
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
        Map<String,Integer> freebeeDiscounts = obtainFreebeeDiscounts(basket);
        Integer basketTotal = Integer.valueOf(0);
        for( Entry<String,Integer> lineItem : basket.entrySet()) {
        	basketTotal += calculateLineItemCost(lineItem, freebeeDiscounts);
        }
        return basketTotal;
    }
	private Map<String,Integer> obtainFreebeeDiscounts(Map<String, Integer> basket) {
		//if stockitem.discount.multiple >= lineItemValue
		//then option to apply freebee to stockitem.discount.freebee (target sku)
		Map<String,Integer> freeSkus = new HashMap<>();
		for( Entry<String,Integer> lineItem : basket.entrySet()) {
			List<Discount> itemDiscounts = catalogue.get(lineItem.getKey()).getDiscounts();
			for( Discount discount :itemDiscounts) {
				if ( !discount.getFreeSku().isEmpty() ) {
					Integer maxFreeSkus = lineItem.getValue()/discount.getMultiple();
					if ( maxFreeSkus > 0) {
						freeSkus.put(discount.getFreeSku(), maxFreeSkus);
					}
				}
			}
		}
		return freeSkus;
	}

	private Integer calculateLineItemCost(Entry<String, Integer> lineItem, Map<String, Integer> freebeeDiscounts) {
		if (!catalogue.get(lineItem.getKey()).getDiscounts().isEmpty() ){
			return calculateBestLineItemDiscount (lineItem, freebeeDiscounts);
		}
		else {
			return lineItem.getValue() * catalogue.get(lineItem.getKey()).getPrice();
		}
	}
	
	private Integer calculateBestLineItemDiscount (Entry<String, Integer> lineItem, 
												Map<String, Integer> freebeeDiscounts) {
		List<Discount> itemDiscounts = catalogue.get(lineItem.getKey()).getDiscounts();
		//set basic price
		Integer currentPrice = 0; //= lineItem.getValue() * catalogue.get(lineItem.getKey()).getPrice();
		Integer remainingItems = lineItem.getValue();
		if ( freebeeDiscounts.containsKey(lineItem.getKey()))
		{
			remainingItems = remainingItems - freebeeDiscounts.get(lineItem.getKey());
		}
		
		for (Discount discount : itemDiscounts) {
			if( discount.getFreeSku().isEmpty())
			{
				Integer discountedIncrement = calculateLineItemDiscount(lineItem, discount, remainingItems);
				currentPrice += discountedIncrement;
			}
		}
		Integer fullPricedItemCost = catalogue.get(lineItem.getKey()).getPrice() * remainingItems;

		return currentPrice + fullPricedItemCost;
	}
	private Integer calculateLineItemDiscount(Entry<String,Integer> lineItem, Discount discount, Integer remainingItems) {
		Integer subTotal = 0;
		if( lineItem.getValue() / discount.getMultiple() > 0 ) {
			subTotal = discount.getCost() * ( lineItem.getValue() / discount.getMultiple());
		}
		remainingItems =  (lineItem.getValue() % discount.getMultiple());
		return subTotal;
	}

	
}



