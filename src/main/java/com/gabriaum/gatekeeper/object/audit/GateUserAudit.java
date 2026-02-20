package com.gabriaum.gatekeeper.object.audit;

import com.gabriaum.gatekeeper.object.user.GateUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Table(name = "audit")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GateUserAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gate_user_id")
    private GateUser gateUser;

    @Column(nullable = false)
    private Instant entranceIn;

    private Instant exitIn;
}