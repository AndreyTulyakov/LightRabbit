/*
 * Copyright (C) 2013 Andrew Tulay
 * @mail: mhyhre@gmail.com
 * 
 * This work is licensed under a Creative Commons 
 * Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * You may obtain a copy of the License at
 *
 *		http://creativecommons.org/licenses/by-nc-nd/3.0/legalcode
 *
 */

package mhyhre.lightrabbit.Scenes;


/**
 * Cloud, simple model
 */

public class Cloud {

	public float SizeX = 0, SizeY = 0;
	public float PosX = 0, PosY = 0;
	public float SpeedX = 0, SpeedY = 0;
	public float Scale = 1;
	public float Rotation = 0;

	// Color modulation
	public float Red = 1, Green = 1, Blue = 1;

	public void SetSize(float px, float py) {
		SizeX = px;
		SizeY = py;
	}

	public void SetColor(float r, float g, float b) {
		Red = r;
		Green = g;
		Blue = b;
	}

	public void SetScale(float ps) {
		Scale = ps;
	}

	public void SetRotation(float pr) {
		Rotation = pr;
	}

	public void SetMoveSpeed(float px, float py) {
		SpeedX = px;
		SpeedY = py;
	}

	public void SetPosition(float px, float py) {
		PosX = px;
		PosY = py;
	}

	public void Update(float px1, float px2, float py1, float py2) {

		if ((PosX + SizeX > px2) || (PosX < px1)) {
			SpeedX *= -1.0f;
		}
		if ((PosY + SizeY > py2) || (PosY < py1)) {
			SpeedY *= -1.0f;
		}

		PosX += SpeedX;
		PosY += SpeedY;
	}

}
