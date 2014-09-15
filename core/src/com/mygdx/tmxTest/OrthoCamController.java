/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
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


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class OrthoCamController extends InputAdapter {
    final OrthographicCamera camera1;
    final OrthographicCamera camera2;
    final Vector3 curr = new Vector3();
    final Vector3 last = new Vector3(-1, -1, -1);
    final Vector3 delta = new Vector3();

    public OrthoCamController (OrthographicCamera camera1, OrthographicCamera camera2) {
        this.camera1 = camera1;
        this.camera2 = camera2;
    }

    @Override
    public boolean touchDragged (int x, int y, int pointer) {
        camera1.unproject(curr.set(x, y, 0));
        if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
            camera1.unproject(delta.set(last.x, last.y, 0));
            delta.sub(curr);
            camera1.position.add(delta.x, delta.y, 0);
            camera2.position.add(delta.x, delta.y, 0);
        }
        last.set(x, y, 0);
        return false;
    }

    @Override
    public boolean touchUp (int x, int y, int pointer, int button) {
        last.set(-1, -1, -1);
        return false;
    }

    boolean up;
    boolean down;
    boolean right;
    boolean left;
    float speed = 0.005f;
    public void update(){
        if (up){
            camera1.position.add(0, -speed, 0);
            camera2.position.add(0, -speed, 0);
        } else if (down){
            camera1.position.add(0, speed, 0);
            camera2.position.add(0, speed, 0);
        }

        if (left){
            camera1.position.add(speed, 0, 0);
            camera2.position.add(speed, 0, 0);
        } else if (right){
            camera1.position.add(-speed, 0, 0);
            camera2.position.add(-speed, 0, 0);
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.RIGHT:
                right = true;
                break;
            case Input.Keys.LEFT:
                left = true;
                break;
            case Input.Keys.UP:
                up = true;
                break;
            case Input.Keys.DOWN:
                down = true;
                break;
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean scrolled(int amount) {
        camera1.zoom += amount*0.025f;
        camera2.zoom += amount*0.025f;
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.RIGHT:
                right = false;
                break;
            case Input.Keys.LEFT:
                left = false;
                break;
            case Input.Keys.UP:
                up = false;
                break;
            case Input.Keys.DOWN:
                down = false;
                break;
        }
        return super.keyDown(keycode);
    }
}