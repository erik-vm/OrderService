package model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull
    @Size(min = 2, max = 50)
    private String orderNumber;
    private List<OrderLine> orderLines;
    private Double total = 0.0;


}



