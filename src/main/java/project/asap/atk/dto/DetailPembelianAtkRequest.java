package project.asap.atk.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class DetailPembelianAtkRequest {
    private Integer atkPurchaseId;
    private Integer atkId;
    @Pattern(regexp = "^[0-9]*$", message = "jumlah harus angka")
    private String jumlah;
    @Pattern(regexp = "^[0-9]*$", message = "harga harus angka")
    private String harga;
    @Pattern(regexp = "^[0-9]*$", message = "subTotal harus angka")
    private String subTotal;
}
