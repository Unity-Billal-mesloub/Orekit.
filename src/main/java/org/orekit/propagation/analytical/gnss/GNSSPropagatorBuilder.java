/* Copyright 2002-2025 CS GROUP
 * Licensed to CS GROUP (CS) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * CS licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.orekit.propagation.analytical.gnss;

import org.orekit.attitudes.AttitudeProvider;
import org.orekit.attitudes.FrameAlignedProvider;
import org.orekit.frames.Frame;
import org.orekit.propagation.Propagator;
import org.orekit.propagation.analytical.gnss.data.GNSSOrbitalElements;

/**
 * Builder for {@link GNSSPropagator}.
 * @author Pascal Parraud
 * @author Luc Maisonobe
 * @since 11.0
 */
public class GNSSPropagatorBuilder {

    /** The GNSS propagation model orbital elements. */
    private final GNSSOrbitalElements<?> orbitalElements;

    /** The attitude provider. */
    private AttitudeProvider attitudeProvider;

    /** The mass. */
    private double mass;

    /** The inertial frame. */
    private final Frame inertial;

    /** The body-fixed frame. */
    private final Frame bodyFixed;

    /** Initializes the builder.
     * <p>The GNSS orbital elements and frames are the only requested parameter to build a GNSSPropagator.</p>
     * <p>The attitude provider is set by default to be aligned with the provided eci frame.<br>
     * The mass is set by default to the
     *  {@link org.orekit.propagation.Propagator#DEFAULT_MASS DEFAULT_MASS}.<br>
     * </p>
     *
     * @param orbitalElements orbital elements
     * @param inertial inertial frame, use to provide the propagated orbit
     * @param bodyFixed body fixed frame, corresponding to the navigation message
     */
    public GNSSPropagatorBuilder(final GNSSOrbitalElements<?> orbitalElements,
                                 final Frame inertial, final Frame bodyFixed) {
        this.orbitalElements  = orbitalElements;
        this.mass             = Propagator.DEFAULT_MASS;
        this.inertial         = inertial;
        this.bodyFixed        = bodyFixed;
        this.attitudeProvider = FrameAlignedProvider.of(inertial);
    }

    /** Sets the attitude provider.
     *
     * @param userProvider the attitude provider
     * @return the updated builder
     */
    public GNSSPropagatorBuilder attitudeProvider(final AttitudeProvider userProvider) {
        this.attitudeProvider = userProvider;
        return this;
    }

    /** Sets the mass.
     *
     * @param userMass the mass (in kg)
     * @return the updated builder
     */
    public GNSSPropagatorBuilder mass(final double userMass) {
        this.mass = userMass;
        return this;
    }

    /** Finalizes the build.
     *
     * @return the built GNSSPropagator
     */
    public GNSSPropagator build() {
        return new GNSSPropagator(orbitalElements, inertial, bodyFixed, attitudeProvider, mass);
    }

}
