precision mediump float;
uniform float time;
uniform vec2 resolution;

void main( void ) {
	
	vec2 position = (gl_FragCoord.xy/resolution.xy) - 0.5 ;
	position.x = fract(0.30-position.x*position.x);
	position.y *= 2.8;
	
	float y = 0.2 * position.y * sin(300.0 * position.y - 20.0 * time *0.01);
	y = 1. / (3333. * abs(position.x - y));
	y += 1./length(355.*length(position - vec2(0., position.y)));

	gl_FragColor=vec4(0,y*5.0,y*10.0,1.0);
}