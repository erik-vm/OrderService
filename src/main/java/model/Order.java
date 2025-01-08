package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class Order {

    private Long id;
    private String orderNumber;
    private List<OrderLine> orderLines;
    private Double total = 0.0;


}



