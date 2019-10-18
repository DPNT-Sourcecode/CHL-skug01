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
		List<Discount> bDiscounts = new ArrayList<>();
		bDiscounts.add(new Discount( 2, 45));
		catalogue.put("B", new StockItem("B", 30, bDiscounts));
		catalogue.put("C", new StockItem("C", 20, null));
		catalogue.put("D", new StockItem("D", 15, null));
		List<Discount> eDiscounts = new ArrayList<>();
		eDiscounts.add(new Discount(2, 0, "B"));
		catalogue.put("E", new StockItem("E", 40, eDiscounts));
		List<Discount> fDiscounts = new ArrayList<>();
		fDiscounts.add(new Discount(3, 0, "F"));
		catalogue.put("F", new StockItem("F", 10, fDiscounts));
		catalogue.put("G", new StockItem("G", 20, null));
		List<Discount> discounts = new ArrayList<>();
		discounts.add(new Discount(5, 45));
		discounts.add(new Discount(10, 80));
		catalogue.put("H", new StockItem("H", 10, discounts));
		discounts.clear();
		catalogue.put("I", new StockItem("I", 35, null));
		catalogue.put("J", new StockItem("J", 60, null));
		discounts.add(new Discount(5, 45));
		discounts.add(new Discount(10, 80));
		catalogue.put("K", new StockItem("K", 80, discounts));
		discounts.clear();
		catalogue.put("M", new StockItem("M", 15, null));
		discounts.add(new Discount(3, 0, "M"));
		catalogue.put("N", new StockItem("N", 80, discounts));
		discounts.clear();
		catalogue.put("O", new StockItem("O", 10, null));
		discounts.add(new Discount(5,200));
		catalogue.put("P", new StockItem("P", 50, discounts));
		discounts.clear();
		discounts.add(new Discount(3,80));
		catalogue.put("Q", new StockItem("Q", 30, discounts));
		discounts.clear();
		discounts.add(new Discount(3, 0, "Q"));
		catalogue.put("R", new StockItem("R", 50, discounts));
		discounts.clear();
		catalogue.put("S", new StockItem("S", 30, null));
		catalogue.put("T", new StockItem("T", 20, null));
		discounts.add(new Discount(4,0,"U"));
		catalogue.put("U", new StockItem("U", 40, discounts));
		discounts.clear();
		discounts.add(new Discount(3,130));
		discounts.add(new Discount(2,90));
		catalogue.put("V", new StockItem("V", 50, discounts));
		discounts.clear();
		catalogue.put("W", new StockItem("W", 20, null));
		catalogue.put("X", new StockItem("X", 90, null));
		catalogue.put("Y", new StockItem("Y", 10, null));
		catalogue.put("Z", new StockItem("Z", 50, null));
		
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
			return calculateBestLineItemDiscount (lineItem, freebeeDiscounts);
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
				Integer discountedIncrement = calculateLineItemDiscount(discount, remainingItems);
				remainingItems = calculateLineItemsRemaining(discount, remainingItems);
//				System.out.println(remainingItems);
				currentPrice += discountedIncrement;
//				System.out.println(discountedIncrement);
			}
		}
		Integer fullPricedItemCost = catalogue.get(lineItem.getKey()).getPrice() * remainingItems;

		return currentPrice + fullPricedItemCost;
	}
	private Integer calculateLineItemDiscount(Discount discount, Integer remainingItems) {
		Integer subTotal = 0;
		if( remainingItems / discount.getMultiple() > 0 ) {
			subTotal = discount.getCost() * ( remainingItems / discount.getMultiple());
		}
		remainingItems =  remainingItems % discount.getMultiple();
		return subTotal;
	}
	private Integer calculateLineItemsRemaining(Discount discount, Integer remainingItems) {
		return remainingItems =  remainingItems % discount.getMultiple();
	}

	
}


