/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tailm.cart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import tailm.car.CarDTO;

/**
 *
 * @author DELL INC
 */
public class cartObject implements Serializable {

    Map<String, CarDTO> cartCar;

    public Map<String, CarDTO> getCartCar() {
        return cartCar;
    }

    public boolean addToCart(CarDTO dto, int quantityDB) {
        if (cartCar == null) {
            cartCar = new HashMap<>();
        }
        if (this.cartCar.containsKey(dto.getCarID())) {
            dto.setRentalDate(cartCar.get(dto.getCarID()).getRentalDate());
            dto.setReturnDate(cartCar.get(dto.getCarID()).getReturnDate());
            dto.setQuantity(cartCar.get(dto.getCarID()).getQuantity() + dto.getQuantity());
            if (dto.getQuantity() > quantityDB) {
                return true;
            }
        }
        this.cartCar.put(dto.getCarID(), dto);
        return false;
    }

    public void deleCarFormCart(String carID) {
        if (cartCar == null) {
            return;
        }

        if (cartCar.containsKey(carID)) {
            cartCar.remove(carID);
            if (cartCar.isEmpty()) {
                cartCar = null;
            }
        }

    }
}
