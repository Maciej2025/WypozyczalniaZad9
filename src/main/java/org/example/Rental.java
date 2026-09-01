package org.example;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rental")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@ToString
public class Rental{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "rent_date")
    private String rentDateTime;
    @Column(name = "return_date")
    private String returnDateTime;

    public Rental copy(){
        return Rental.builder()
                .id(this.id)
                .vehicle(this.vehicle)
                .user(this.user)
                .rentDateTime(this.rentDateTime)
                .returnDateTime(this.returnDateTime)
                .build();
    }

    public boolean isActive(){
        return returnDateTime == null || returnDateTime.isBlank();
    }
    public String getVehicleId(){
        if(vehicle != null){
            return vehicle.getId();
        }else{
            return null;
        }
    }

    public String getUserId(){
        if(user != null){
            return user.getId();
        }else{
            return null;
        }
    }
}