package idb2camp.b2campjufrin.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Region  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer regionId;
    @Column(name = "region_name")
    private String regionName;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "region", cascade = CascadeType.ALL)
    private List<Country> country;
}
