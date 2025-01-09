package model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLine {
    @NotNull
    @Size(min = 2, max = 100)
    private String description;
    @NotNull
    @DecimalMin("0.1")
    private Double price;
}
