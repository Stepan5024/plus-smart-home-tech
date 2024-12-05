package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.shoppingStore.enums.ProductCategory;
import ru.yandex.practicum.shoppingStore.enums.ProductState;
import ru.yandex.practicum.shoppingStore.enums.QuantityState;

import java.util.UUID;

@Entity
@Table(name = "products")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID productId;
    String productName;
    String description;
    String imageSrc;
    @Enumerated(EnumType.STRING)
    QuantityState quantityState;
    @Enumerated(EnumType.STRING)
    ProductState productState;
    int rating;
    @Enumerated(EnumType.STRING)
    ProductCategory productCategory;
    double price;
}
