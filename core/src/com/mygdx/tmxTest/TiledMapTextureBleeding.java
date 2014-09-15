/*******************************************************************************
 * Copyright 2014 Piotr Jastrzebski.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.mygdx.tmxTest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TiledMapTextureBleeding extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

    private TiledMap mapPadding;
    private TiledMap mapNoPadding;
    private OrthographicCamera cameraPadding;
    private OrthographicCamera cameraNoPadding;
    private OrthogonalTiledMapRenderer rendererPadding;
    private OrthogonalTiledMapRenderer rendererNoPadding;

    private OrthoCamController mapController;
    private BitmapFont font;

    @Override
	public void create () {
		batch = new SpriteBatch();
        font = new BitmapFont();

        mapPadding = new TmxMapLoader().load("data/map_padding.tmx");
        mapNoPadding = new TmxMapLoader().load("data/map_no_padding.tmx");
        float unitScale = 1 / 32f;
        rendererPadding = new OrthogonalTiledMapRenderer(mapPadding, unitScale);
        rendererNoPadding = new OrthogonalTiledMapRenderer(mapNoPadding, unitScale);

        cameraPadding = new OrthographicCamera();
        cameraPadding.setToOrtho(false, 30, 20);
        cameraPadding.update();
        cameraNoPadding = new OrthographicCamera();
        cameraNoPadding.setToOrtho(false, 30, 20);
        cameraNoPadding.update();

        mapController = new OrthoCamController(cameraPadding, cameraNoPadding);

        Gdx.input.setInputProcessor(mapController);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapController.update();

        int h = Gdx.graphics.getHeight();
        int halfW = Gdx.graphics.getWidth() / 2;

        Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
        Gdx.gl.glScissor(0, 0, halfW-8, h);

        cameraPadding.update();
        rendererPadding.setView(cameraPadding);
        rendererPadding.render();

        Gdx.gl.glScissor(halfW+8, 0, halfW, h);

        cameraNoPadding.update();
        rendererNoPadding.setView(cameraNoPadding);
        rendererNoPadding.render();

        Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);

        batch.begin();
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        font.draw(batch, "MAP WITH PADDING: ", Gdx.graphics.getWidth()/6, 20);
        font.draw(batch, "MAP WITHOUT PADDING", Gdx.graphics.getWidth()*2/3, 20);
        batch.end();
	}

    @Override
    public void dispose() {
        mapPadding.dispose();
        mapNoPadding.dispose();
    }
}
