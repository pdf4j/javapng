/*
com.sixlegs.image.png - Java package to read and display PNG images
Copyright (C) 1998-2005 Chris Nokleberg

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Library General Public
License as published by the Free Software Foundation; either
version 2 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Library General Public License for more details.

You should have received a copy of the GNU Library General Public
License along with this library; if not, write to the
Free Software Foundation, Inc., 59 Temple Place - Suite 330,
Boston, MA  02111-1307, USA.
*/

package com.sixlegs.png;

import java.io.*;
import java.util.Map;

class Chunk_sRGB
extends PngChunk
{
    public Chunk_sRGB()
    {
        super(sRGB);
    }

    public void read(PngInputStream in, int length, PngImage png)
    throws IOException
    {
        checkLength(length, 1);
        int intent = in.readByte();
        Map props = png.getProperties();
        if (props.containsKey(PngImage.ICC_PROFILE_NAME))
            throw new PngWarning("Conflicting iCCP and sRGB chunks found");
        props.put(PngImage.RENDERING_INTENT, Integers.valueOf(intent));
        props.put(PngImage.GAMMA, new Float(0.45455));
        props.put(PngImage.WHITE_POINT_X, new Float(0.3127f));
        props.put(PngImage.WHITE_POINT_Y, new Float(0.329f));
        props.put(PngImage.RED_X, new Float(0.64f));
        props.put(PngImage.RED_Y, new Float(0.33f));
        props.put(PngImage.GREEN_X, new Float(0.3f));
        props.put(PngImage.GREEN_Y, new Float(0.6f));
        props.put(PngImage.BLUE_X, new Float(0.15f));
        props.put(PngImage.BLUE_Y, new Float(0.06f));
    }
}