@echo off
:: this stores current dir
pushd \
:: go to libgdx dir...
cd C:\DEV\libgdx\libgdx-nightly-latest\
@echo on
:: pack the images with padding
java -cp gdx.jar;extensions/gdx-tools/gdx-tools.jar com.badlogic.gdx.tools.texturepacker.TexturePacker ^
C:\Drive\workspace\TiledMapTextureBleeding\raw_assets\png_padding\ ^
C:\Drive\workspace\TiledMapTextureBleeding\android\assets\data\pack\ ^
pack_padding
:: pack the images without padding
java -cp gdx.jar;extensions/gdx-tools/gdx-tools.jar com.badlogic.gdx.tools.texturepacker.TexturePacker ^
C:\Drive\workspace\TiledMapTextureBleeding\raw_assets\png_nopadding\ ^
C:\Drive\workspace\TiledMapTextureBleeding\android\assets\data\pack\ ^
pack_no_padding

@echo off
:: this restores last dir
popd
@echo on
