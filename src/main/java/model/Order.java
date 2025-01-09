package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@With
@Table(name="orders")
public class Order extends BaseEntity{

    private Long id;
    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "order_number")
    private String orderNumber;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_lines", joinColumns =
    @JoinColumn(name = "order_id", referencedColumnName = "id"))
    private List<OrderLine> orderLines;
    private double total = 0.0;


}



