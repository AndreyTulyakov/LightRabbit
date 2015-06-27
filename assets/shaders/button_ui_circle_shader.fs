precision mediump float;
uniform float time;
uniform vec2 resolution;

void main( void ) {
	
	vec2 position = (gl_FragCoord.xy/resolution.xy) - 0.5 ;
	position.x = fract(0.3-position.x*position.x);
	position.y *= 3.2;
	
	float y = 0.8 * position.y * sin(30.0 * position.y - 20.0 * time *0.01);
	y = 1. / (1000. * abs(position.x - y));
	y -= 1./length(350.*length(position - vec2(0., position.y)));

	gl_FragColor=vec4(0.0,y*4.0,y*10.0,1.0);


}